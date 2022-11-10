package com.betterLife.habitsBuilder.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betterLife.habitsBuilder.model.DayLife;
import com.betterLife.habitsBuilder.model.Task;
import com.betterLife.habitsBuilder.repository.DayLifeRepository;
import com.betterLife.habitsBuilder.repository.TaskRepository;

@Service
public class HabitsBuilderServiceImpl implements HabitsBuilderService {

    @Autowired
    TaskFinderByDateService taskFinderByDate;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    DayLifeRepository dayLifeRepository;

    @Override
    public Task saveTask(Task t) {
        return taskRepository.save(t);
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

    public ArrayList<Task> getTasksByDate( LocalDate date ) {
        ArrayList< Task > allTasks = ( ArrayList< Task > ) taskRepository.findAll();

        ArrayList< Task > tasksWithDate = ( ArrayList< Task > ) allTasks.stream()
            .filter( task -> taskFinderByDate.taskContainsDate( task, date ) )
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

    
    
}
