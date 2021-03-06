package com.iscas.smurfs.modules.admin.biz;

import java.util.List;

public interface IBaseBiz<T> {
    public int deleteByPrimaryKey(Integer id);

    public int insert(T entity);

    public int insertSelective(T entity);

    public T selectByPrimaryKey(Integer categoryId);

    public int updateByPrimaryKeySelective(T record);
    public int updateByPrimaryKey(T record);

    public T selectOne(T entity);

    public List<T> selectAll();
}
