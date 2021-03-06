package com.runner.service;

import com.runner.po.OrderForm;

import java.util.List;

public interface OrderFormService {
    //根据id查询订单
    public OrderForm findOrderFormById(Integer id);
    //根据委托人id查询订单
    public OrderForm findOrderFormByClientId(Integer client_id);
    //根据接单/受托人id查询订单
    public OrderForm findOrderFormByTrusteeId(Integer trustee_id);
    //查询订单状态为0且按时间最新顺序排列的结果，用于对所有用户显示
    public List<OrderForm> showOrderFormLatest(int index);
    //取货地点排序
    public List<OrderForm> showOrderFormAds(int index);
    //查收益排序
    public List<OrderForm> showOrderFormMoney(int index);
    //货物大小顺序排序
    public List<OrderForm> showOrderFormSize(int index);
    //查询订单状态为0的订单数量
    public int getOrderFormNum();
    //判断订单号是否是自己的
    public boolean isMyOrderForm(int order_id,int user_id);
    //获取某用户接单数state=08
    public int getUserPickNum(int user_id);
    //获取接单信息
    public List<OrderForm> getPickOrderForm_info(int trustee_id);
    //获取发单信息
    public List<OrderForm> getDeputeOrderForm_info(int client_id);
    //获取该用户发单次数
    public int getPutOrderNum(int client_id);
    //获取用户的历史订单
    public List<OrderForm> getHistoryOrderForm(int userId);
    //判断用户是否存在为评价发单者的订单
    public boolean isExistNotCommentForm(int client_id);

    //新增订单
    public boolean addOrderForm(OrderForm orderForm);
    //新增前检验订单号是否存在
    public boolean isOrderNumExist(String order_num);

    // 更新订单
    public void updateOrderForm(OrderForm orderForm);
    /**
     * 删
     */
    public void deleteOrderForm(OrderForm orderForm);
    public void deleteOrderFormById(int orderform_id);

}
