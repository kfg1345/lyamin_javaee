package DAO;

import domain.Order;
import DAO.Connection.ConnectionBuilder;
import DAO.Connection.DbConnectionBuilder;
import Models.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Order_DAO implements RepositoryDAO<Order>  {
    public Order_DAO() {}
    private static final String select_all_orders = "SELECT * FROM order ORDER BY id";
    private static final String select_order_ById = "SELECT * FROM order WHERE id = ?";
    private static final String insert_order = "INSERT INTO order (id, type, veriety, currency, tiker, count, number, data, duration) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String edit_order = "UPDATE plan SET type = ?, veriety=?, currency=?, tiker=?, count=?, number=?, data=?, duration=& WHERE id = ?";
    private static final String delete_order = "DELETE FROM order WHERE id = ?";
    private ConnectionBuilder builder = new DbConnectionBuilder();
    private Connection getConnection() throws SQLException {return builder.getConnection();}
    @Override
    public Long insert(Order order) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(insert_order)) {
            pst.setString(1, order.getOrderType());
            pst.setString(2, order.getOrderVeriety());
            pst.setString(3, order.getCurrency());
            pst.setString(4, order.getTiker());
            pst.setString(5, order.getCount().toString());
            pst.setLong(6, order.getNumber());
            pst.setString(7, order.getData());
            pst.setString(8, order.getDuration());
            pst.setLong(9, order.getId());
            pst.executeUpdate();
        } catch (Exception e) {System.out.println(e.getMessage());}
        return null;
    }
    @Override
    public void update(Order order){
        try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(edit_order)) {
            pst.setString(1, order.getOrderType());
            pst.setString(2, order.getOrderVeriety());
            pst.setString(3, order.getCurrency());
            pst.setString(4, order.getTiker());
            pst.setString(5, order.getCount().toString());
            pst.setLong(6, order.getNumber());
            pst.setString(7, order.getData());
            pst.setString(8, order.getDuration());
            pst.setLong(9, order.getId());
            pst.executeUpdate();
        } catch (Exception e) {System.out.println(e.getMessage());}
    }

    @Override
    public void delete(Long Id){
        try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(delete_order)) {
            pst.setLong(1, Id);
            pst.executeUpdate();
        } catch (Exception e) {System.out.println(e.getMessage());}
    }

    @Override
    public Order findById(Long Id) {
        Order orders = null;
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(select_order_ById);
            pst.setLong(1, Id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {orders = fill(rs);}
            rs.close();
            pst.close();
        } catch (Exception e) {System.out.println(e);}
        return orders;
    }
    @Override
    public List<Order> findAll() {
        List<Order> list = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(select_all_orders);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {list.add(fill(rs));}
        } catch (Exception e) {System.out.println(e.getMessage());}
        return list;
    }

    private Order fill(ResultSet rs) throws SQLException {
        Order plan = new Order();
        plan.setId(rs.getLong("id"));
        plan.setOrderType(rs.getString("type"));
        plan.setOrderVeriety(rs.getString("veriety"));
        plan.setTiker(rs.getString("tiker"));
        plan.setNumber(rs.getLong("number"));
        plan.setCount(rs.getLong("count"));
        plan.setData(rs.getString("data"));
        plan.setDuration(rs.getString("duration"));
        return plan;
    }
    public Orders FindById(Long id, List<Orders> orders) {
        if (orders != null) {
            for (Orders c: orders) {
                if (c.getId()==id) {
                    return c;
                }
            }
        }
        else {
            return null;
        }
        return null;
    }
}
