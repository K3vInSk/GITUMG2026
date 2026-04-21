/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.CreateConnection;
import model.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author kevinsica
 */
public class EmpleadoDAO {
    
    private final CreateConnection connFactory = new CreateConnection();

    public List<Empleado> obtenerTodos() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection conn = connFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("puesto"),
                        rs.getDouble("salario")
                );
                lista.add(emp);
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean guardar(Empleado emp) {
        String sql = "INSERT INTO empleados (nombre, apellido, puesto, salario) VALUES (?, ?, ?, ?)";

        try (Connection conn = connFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellido());
            ps.setString(3, emp.getPuesto());
            ps.setDouble(4, emp.getSalario());

            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean actualizar(Empleado emp) {
        String sql = "UPDATE empleados SET nombre=?, apellido=?, puesto=?, salario=? WHERE id=?";

        try (Connection conn = connFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellido());
            ps.setString(3, emp.getPuesto());
            ps.setDouble(4, emp.getSalario());
            ps.setInt(5, emp.getId());

            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id=?";

        try (Connection conn = connFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}