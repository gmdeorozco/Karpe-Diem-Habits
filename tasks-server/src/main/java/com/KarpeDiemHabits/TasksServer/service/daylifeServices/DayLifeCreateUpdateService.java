package com.KarpeDiemHabits.TasksServer.service.daylifeServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.repository.DayLifeRepository;
import com.KarpeDiemHabits.TasksServer.repository.TaskRepository;

@Service
public class DayLifeCreateUpdateService {


    @Autowired
    TaskRepository taskRepository;

    @Autowired
    DayLifeRepository dayLifeRepository;

    @Autowired
    DayLifeGetterService dayLifeGetterService;
 
    public DayLife saveDayLife(DayLife d) {
        return dayLifeRepository.save(d);
    }
       
    public boolean deleteDayLifeById(long id) {
        try {
            Optional<DayLife> d = dayLifeRepository.findById(id);
            dayLifeRepository.delete(d.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public ArrayList<DayLife> createDayLifesByInterval(Task task, LocalDate initialDate, LocalDate endDate){
        
        ArrayList < DayLife > dayLifes = new ArrayList<>();
        
        for( LocalDate date = initialDate; 
                date.isBefore( endDate ) || date.isEqual( endDate ); 
                date = date.plusDays(1 ) ){

                    if ( !task.dayOfWeekIsActive(date.getDayOfWeek()) ) { continue; }
                    

                    DayLife existentDayLife = dayLifeGetterService.getDayLifeByDate(date);

                    if ( existentDayLife == null ){
                        DayLife dl = new DayLife();
                        dl.setDate(date);
                        dl.setTasks(new ArrayList<>());
                        dl.getTasks().add(task);
                        dl = dayLifeRepository.save( dl );
                        
                        dayLifes.add( dl );
                    }else{
                        
                        existentDayLife.getTasks().add(task);
                        dayLifeRepository.save(existentDayLife);
                    }

                    


        }
        return dayLifes;
    }
    
    public void recalculateDayLifes(Task task) {
        ArrayList< DayLife > relevantDayLifes = 
         createDayLifesByInterval(task, task.getInitialDate(), task.getEndDate());
        task.setDayLifes( relevantDayLifes );
    }

    public void recalculateFutureDayLifes(Task task) {
        LocalDate today = LocalDate.now();
        ArrayList< DayLife > relevantDayLifes = 
        createDayLifesByInterval(task, today, task.getEndDate());
        task.setDayLifes( relevantDayLifes );
    }

   
    
}
