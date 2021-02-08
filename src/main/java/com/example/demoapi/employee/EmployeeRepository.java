package com.example.demoapi.employee;

import com.example.demoapi.common.ApplicationConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Collection<Employee> getEmployee() {

        String sql = "SELECT * FROM TBL_EMPLOYEES where is_delete = 'N'";

        return this.namedParameterJdbcTemplate.query(sql, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet rs, int i) throws SQLException {

                return Employee.builder()
                        .id(rs.getLong("ID"))
                        .firstName(rs.getString("FIRST_NAME"))
                        .lastName(rs.getString("LAST_NAME"))
                        .email(rs.getString("EMAIL"))
                        .isDelete(rs.getString("IS_DELETE"))
                        .build();
            }
        });

    }


    public int saveEmployee(Employee request) {

        String sql = "INSERT INTO TBL_EMPLOYEES (first_name, last_name, email) " +
                "VALUES (:firstName, :lastName, :email)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();


        parameterSource.addValue("firstName", request.getFirstName());
        parameterSource.addValue("lastName", request.getLastName());
        parameterSource.addValue("email", request.getEmail());

        return this.namedParameterJdbcTemplate.update(sql,parameterSource);

    }

    public int updateEmployee(Employee request)  {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TBL_EMPLOYEES SET ID = :id");

        if (StringUtils.hasText(request.getFirstName())) {
            sql.append(", first_name = :firstName");
            parameters.addValue("firstName", request.getFirstName());
        }
        if (StringUtils.hasText(request.getLastName())) {
            sql.append(", last_name = :lastName");
            parameters.addValue("lastName", request.getLastName());
        }
        if (StringUtils.hasText(request.getEmail())) {
            sql.append(", email = :email");
            parameters.addValue("email", request.getEmail());
        }

        sql.append(" WHERE ID = :id;");
        parameters.addValue("id", request.getId());
        return this.namedParameterJdbcTemplate.update(sql.toString(),parameters);

    }


    public int deleteEmployee(Long id){
        StringBuilder sql = new StringBuilder();

        sql.append("update TBL_EMPLOYEES  set is_delete = :isDelete \r\n");
        sql.append("where id = :id \r\n");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("isDelete", ApplicationConstant.FLAG.YES);
        parameterSource.addValue("id", id);

        return this.namedParameterJdbcTemplate.update(sql.toString(),parameterSource);


    }

    public Optional<Employee> inquiryEmployee(Long id) {

        try {

            String sql = "SELECT * FROM TBL_EMPLOYEES WHERE ID = :id " +
                    "and is_delete = 'N'";

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();

            parameterSource.addValue("id", id);
            return this.namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new RowMapper<Optional<Employee>>() {
                @Override
                public Optional<Employee> mapRow(ResultSet rs,int i) throws SQLException {

                    return Optional.of(Employee.builder()
                            .id(rs.getLong("ID"))
                            .firstName(rs.getString("FIRST_NAME"))
                            .lastName(rs.getString("LAST_NAME"))
                            .email(rs.getString("EMAIL"))
                            .isDelete(rs.getString("IS_DELETE"))
                            .build());
                }
            });
        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();
        }
    }
}
