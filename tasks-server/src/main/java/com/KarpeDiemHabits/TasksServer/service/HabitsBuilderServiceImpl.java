package com.KarpeDiemHabits.TasksServer.service;

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
public class HabitsBuilderServiceImpl implements HabitsBuilderService {


    @Autowired
    TaskRepository taskRepository;

    @Autowired
    DayLifeRepository dayLifeRepository;

    @Override
    public Task saveTask(Task t) {

        recalculateDayLifes(taskRepository.save(t));
        return t;
    }

    @Override
    public ArrayList<Task> getAllTask() {
        return (ArrayList<Task>) taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public boolean deleteTaskById(long id) {
        try {
            Optional<Task> t = getTaskById(id);
            taskRepository.delete(t.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ArrayList<Task> getTasksByDate( LocalDate date ) {
        ArrayList< Task > allTasks = ( ArrayList< Task > ) taskRepository.findAll();

        ArrayList< Task > tasksWithDate = ( ArrayList< Task > ) allTasks.stream()
            .filter( task -> taskContainsDate( task, date ) )
            .collect(Collectors.toList());

        return tasksWithDate;
    }

    @Override
    public DayLife saveDayLife(DayLife d) {
        return dayLifeRepository.save(d);
    }

    @Override
    public ArrayList<DayLife> getAllDayLife() {
        return (ArrayList<DayLife>) dayLifeRepository.findAll();
    }

    @Override
    public Optional<DayLife> getDayLifeById(long id) {
        return dayLifeRepository.findById(id);
    }

    @Override
    public boolean deleteDayLifeById(long id) {
        try {
            Optional<DayLife> d = getDayLifeById(id);
            dayLifeRepository.delete(d.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public DayLife getDayLifeByDate(LocalDate date) {
        ArrayList<DayLife> allDayLifes = (ArrayList<DayLife>) dayLifeRepository.findAll();

        ArrayList<DayLife> dl = (ArrayList<DayLife>) allDayLifes.stream()
            .filter( daylife -> daylife.getDate().equals(date))
            .collect(Collectors.toList());

        return dl.size() > 0 ? dl.get(0) : null;
    }

    @Override
    public ArrayList<DayLife> createDayLifesByInterval(Task task, LocalDate initialDate, LocalDate endDate){
        
        ArrayList < DayLife > dayLifes = new ArrayList<>();
        
        for( LocalDate date = initialDate; 
                date.isBefore( endDate ) || date.isEqual( endDate ); 
                date = date.plusDays(1 ) ){

                    if ( !task.dayOfWeekIsActive(date.getDayOfWeek()) ) { continue; }
                    

                    DayLife existentDayLife = getDayLifeByDate(date);

                    if ( existentDayLife == null ){
                        DayLife dl = new DayLife();
                        dl.setDate(date);
                        dl.setTasks(new ArrayList<>());
                        dl.getTasks().add(task);
                        dl = saveDayLife( dl );
                        
                        dayLifes.add( dl );
                    }else{
                        
                        existentDayLife.getTasks().add(task);
                        saveDayLife(existentDayLife);
                    }

                    


        }
        return dayLifes;
    }

    @Override
    public void recalculateDayLifes(Task task) {
        ArrayList< DayLife > relevantDayLifes = 
         createDayLifesByInterval(task, task.getInitialDate(), task.getEndDate());
        task.setDayLifes( relevantDayLifes );
    }

    @Override
    public void recalculateFutureDayLifes(Task task) {
        LocalDate today = LocalDate.now();
        ArrayList< DayLife > relevantDayLifes = 
        createDayLifesByInterval(task, today, task.getEndDate());
        task.setDayLifes( relevantDayLifes );
    }

    //CHECK IF TASK IS CONTAINED IN A DAYLIFE
    @Override
    public boolean findTaskWithinDayLife( DayLife dayLife, Long taskId ){

        boolean isOnTasks = dayLife.getTasks().stream()
        .anyMatch( task -> task.getId() == taskId);

        boolean isOnApprovedTasks = dayLife.getApprovedTasks().stream()
        .anyMatch( task -> task.getId() == taskId );

        return isOnTasks || isOnApprovedTasks;
            
    }
    
    
    @Override
    public DayLife approveTask( Long dayLifeId, Long taskId ){
        Optional < DayLife > dayLife = getDayLifeById( dayLifeId );
        Optional < Task > task = getTaskById( taskId );
        
        if ( dayLife.isPresent() && task.isPresent() &&
            findTaskWithinDayLife( dayLife.get(), taskId ) &&
            dayLife.get().getTasks().remove( task.get() ) &&
            dayLife.get().getApprovedTasks().add( task.get() )){
                return saveDayLife(dayLife.get());
            }
        return null;
    }

    @Override
    public DayLife failTask(Long dayLifeId, Long taskId) {
        Optional <DayLife> dayLife = getDayLifeById( dayLifeId );
        Optional <Task> task = getTaskById( taskId );
        
        if( dayLife.isPresent() && task.isPresent() &&
            findTaskWithinDayLife(dayLife.get(), taskId) &&
            dayLife.get().getApprovedTasks().remove(task.get()) &&
            dayLife.get().getTasks().add(task.get()) ){
           
                return saveDayLife(dayLife.get());
        } else{
            return null;
        }

    }

    //CHECK IF TASK CONTAINS  A DATE
    @Override
    public boolean taskContainsDate( Task task, LocalDate date ){
        LocalDate taskInitialDate = task.getInitialDate();
        LocalDate taskEndDate = task.getEndDate();

        return ( taskInitialDate.equals(date) 
            || taskEndDate.equals(date) 
            || ( taskInitialDate.isBefore( date ) && taskEndDate.isAfter( date ) ));
    }
    
}
