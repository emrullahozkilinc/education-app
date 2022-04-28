package com.example.educationapp.conf;

import com.example.educationapp.enums.UserRoles;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.*;
import java.util.Arrays;

public class RolesEnumPostgreConf extends EnumType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class returnedClass() {
        return UserRoles[].class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Array array = rs.getArray(names[0]);
        String[] arr = (String[]) array.getArray();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].substring(5,arr[i].length());
        }
        return array != null ? arr : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) {
        Array array = null;
        try {
            array = session.connection().createArrayOf("roles", (UserRoles[])value);
            st.setArray(index, array);
        } catch (SQLException e) {
            System.err.println("sqlexception error:");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (HibernateException e) {
            System.err.println("hibernate error:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
