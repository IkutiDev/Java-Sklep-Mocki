package mocks;

import entities.Item;
import validators.ItemValidation;

public class ItemValidatorTrue implements ItemValidation {
    @Override
    public boolean isValid(Item item){
        return true;
    }
}
