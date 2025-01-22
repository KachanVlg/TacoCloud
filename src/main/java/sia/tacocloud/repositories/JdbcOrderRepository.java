package sia.tacocloud.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import sia.tacocloud.domainEntities.Ingredient;
import sia.tacocloud.domainEntities.IngredientRef;
import sia.tacocloud.domainEntities.Taco;
import sia.tacocloud.domainEntities.TacoOrder;

import javax.swing.*;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcOrderRepository implements OrderRepository{


    private JdbcOperations jdbcOperations;


    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {

        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco_Order "
                        + "(delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number, "
                        + "cc_expiration, cc_cvv, placed_at) "
                        + "values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );

        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryCity(),
                                order.getDeliveryState(),
                                order.getDeliveryZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i=0;
        for(Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }


    private long saveTaco(Long orderId, int orderKey, Taco taco) {

        taco.setCreatedAt(new Date());

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
            "insert into Taco "
            + "(name, taco_order, taco_order_key, created_at) "
            + "values (?,?,?,?)",
            Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.TIMESTAMP
        );

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
            Arrays.asList(taco.getName(), orderId, orderKey, taco.getCreatedAt())
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);

        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        List<IngredientRef> ingredientsRefs = taco.getIngredients()
                .stream().map(ingredient -> new IngredientRef(ingredient.getId()))
                .collect(Collectors.toList());

        saveIngredientsRefs(tacoId, ingredientsRefs);

        return tacoId;
    }



    private void saveIngredientsRefs(long tacoId, List<IngredientRef> ingredientsRefs) {

        int key = 0;
        for(IngredientRef ingredientRef : ingredientsRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) "
            + "values (?,?,?)", ingredientRef.getIngredient(), tacoId, key++);
        }

    }
}
