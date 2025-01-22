package sia.tacocloud.repositories;


import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.domainEntities.Taco;
import sia.tacocloud.domainEntities.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {}
