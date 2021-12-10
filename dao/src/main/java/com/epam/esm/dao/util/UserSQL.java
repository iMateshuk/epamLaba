package com.epam.esm.dao.util;

public enum UserSQL {
  SELECT_ALL("SELECT u FROM User u"),
  SELECT_ID("SELECT u FROM User u where id=:id"),
  COUNT_ALL("SELECT count(id) FROM User u"),

  SELECT_USED_TAGS("SELECT t.*, COUNT(*) as count FROM gc.tags t " +
      "inner join gc.gc_tag gt on t.id=gt.tag_id " +
      "inner join gc.gift_certificate c on gt.gc_id=c.id " +
      "inner join gc.orders o on c.id=o.cert_id " +
      "where o.user_id=:id " +
      "group by t.name " +
      "having max(o.cost) = (SELECT MAX(o.cost) FROM gc.orders o inner join gc.users u on u.id=o.user_id where u.id=:id) " +
      "and " +
      "count = (SELECT MAX(counts) from " +
      "(SELECT COUNT(*) as counts FROM gc.tags ts " +
      "inner join gc.gc_tag gts on ts.id=gts.tag_id " +
      "inner join gc.gift_certificate cs on gts.gc_id=cs.id " +
      "inner join gc.orders os on cs.id=os.cert_id " +
      "where os.user_id=:id group by ts.name ) as tn) "),

  COUNT_USED_TAGS("SELECT count(*) from (select t.*, COUNT(*) as count FROM gc.tags t " +
      "inner join gc.gc_tag gt on t.id=gt.tag_id " +
      "inner join gc.gift_certificate c on gt.gc_id=c.id " +
      "inner join gc.orders o on c.id=o.cert_id " +
      "where o.user_id=:id " +
      "group by t.name " +
      "having max(o.cost) = (SELECT MAX(o.cost) FROM gc.orders o inner join gc.users u on u.id=o.user_id where u.id=:id) " +
      "and " +
      "count = (SELECT MAX(counts) from (SELECT COUNT(ts.name) as counts FROM gc.tags ts " +
      "inner join gc.gc_tag gts on ts.id=gts.tag_id " +
      "inner join gc.gift_certificate cs on gts.gc_id=cs.id " +
      "inner join gc.orders os on cs.id=os.cert_id " +
      "where os.user_id=:id group by ts.name) as tn) ) as agg"),

  ;

  private final String sql;

  UserSQL(String string) {
    this.sql = string;
  }

  public String getSQL() {
    return sql;
  }
}
