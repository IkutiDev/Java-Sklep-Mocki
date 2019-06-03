package repositories;

import entities.Entity;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;

public class InMemoryGenericRepo<T extends Entity> implements GenericRepo<T>{
    private List<T> entities;
    public InMemoryGenericRepo(){
        entities=new ArrayList<>();
    }
    @Override
    public T getById(int id){
        return Iterables.tryFind(this.entities, x -> {
            assert x != null;
            return x.getId()==id;
        }).orNull();
    }
    @Override
    public List<T> getAll(){
        return new ArrayList<>(entities);
    }
    @Override
    public boolean add(T entity){
        if(this.getById(entity.getId())!=null){
            return false;
        }
        entities.add(entity);
        return true;
    }
    @Override
    public boolean update(T entity){
        T oldEntity = Iterables.tryFind(this.entities, x -> {
            assert x != null;
            return x.getId() == entity.getId();
        }).orNull();
        if (oldEntity == null)
        {
            return false;
        }

        entities.remove(oldEntity);
        entities.add(entity);
        return true;
    }
    @Override
    public boolean delete(T entity){
        if(this.getById(entity.getId())==null){
            return false;
        }
        entities.remove(entity);
        return true;
    }
}
