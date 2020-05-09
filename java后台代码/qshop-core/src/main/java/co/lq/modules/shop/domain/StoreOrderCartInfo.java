package co.lq.modules.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @ClassName StoreOrderCartInfo
 * @Author billy
 * @Date 2019/10/14
 **/

@Entity
@Data
@Table(name = "store_order_cart_info")
public class StoreOrderCartInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long   id;

    @Column(name = "oid")
    private Long   oid;

    @Column(name = "cart_id")
    private Long   cartId;

    @Column(name = "cart_info")
    private String cartInfo;

    @Column(name = "unique")
    private String unique;

}
