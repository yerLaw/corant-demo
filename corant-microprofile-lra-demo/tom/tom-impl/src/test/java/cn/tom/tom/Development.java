package cn.tom.tom;

import org.corant.modules.jpa.hibernate.orm.HibernateOrmDeveloperKits;

/**
 * cps-opms <br>
 *
 * @auther sushuaihao 2019年5月14日
 * @since
 */
public class Development {

  public static void main(String... strings) {
    HibernateOrmDeveloperKits.stdoutUpdateSchema("tomPu");
  }
}
