package mocks;

import entities.Item;
import validators.ItemValidation;

public class ItemValidatorFalse implements ItemValidation {
    @Override
    public boolean isValid(Item item){
        return false;
    }
}
