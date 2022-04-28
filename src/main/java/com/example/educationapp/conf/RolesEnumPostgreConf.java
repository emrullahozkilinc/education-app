package com.example.educationapp.conf;

import com.example.educationapp.enums.UserRoles;
import com.example.educationapp.exception.UserNotFoundException;
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

    //TODO: This is only working for one role. It will be change for multiple roles.
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        if (rs.wasNull()) {
            System.err.println("rs boş");
            throw new SQLException();
        }
        if (rs.getArray(names[0]) == null) {
            throw new UserNotFoundException("Kullanıcı bulunamadı.");
        }

        Array array = rs.getArray(names[0]);
        String role = ((String[]) array.getArray())[0];
        role = role.substring(5,role.length());

        return role;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        Array array = array = session.connection().createArrayOf("roles", (UserRoles[])value);
        st.setArray(index, array);
    }
}
