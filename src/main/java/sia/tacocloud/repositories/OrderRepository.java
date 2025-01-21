package sia.tacocloud.repositories;


import sia.tacocloud.domainEntities.Taco;
import sia.tacocloud.domainEntities.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
