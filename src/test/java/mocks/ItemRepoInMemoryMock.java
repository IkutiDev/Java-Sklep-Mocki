package mocks;

import entities.Item;
import repositories.InMemoryGenericRepo;
import repositories.ItemRepo;

public class ItemRepoInMemoryMock extends InMemoryGenericRepo<Item> implements ItemRepo {
}
