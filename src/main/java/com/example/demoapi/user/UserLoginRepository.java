package com.example.demoapi.user;

import com.example.demoapi.common.ApplicationConstant;
import com.example.demoapi.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserLoginRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<User> inquiryUser(UserLoginRequestModel requestModel) {

        try {

            String sql = "SELECT * FROM USER \n" +
                    "WHERE ID = :id \n" +
                    "   and user_name = :userName \n" +
                    "   and password = :password \n" +
                    "   and lock_user = 'N' ;";

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();

            parameterSource.addValue("id", requestModel.getId());
            parameterSource.addValue("userName", requestModel.getUserName());
            parameterSource.addValue("password", requestModel.getPassword());

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new RowMapper<Optional<User>>() {
                @Override
                public Optional<User> mapRow(ResultSet rs, int i) throws SQLException {

                    return Optional.of(User.builder()
                            .id(rs.getLong("ID"))
                            .userName(rs.getString("USER_NAME"))
                            .email(rs.getString("EMAIL"))
                            .password(rs.getString("PASSWORD"))
                            .lockUser(rs.getString("LOCK_USER"))
                            .build());
                }
            });
        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();
        }
    }

    public Optional<User> inquiryUserByUserName(UserLoginRequestModel requestModel) {

        try {

            String sql = "SELECT * FROM USER \n" +
                    "WHERE ID = :id \n" +
                    "   and user_name = :userName \n" +
                    "   and lock_user = 'N' ;";

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();

            parameterSource.addValue("id", requestModel.getId());
            parameterSource.addValue("userName", requestModel.getUserName());

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new RowMapper<Optional<User>>() {
                @Override
                public Optional<User> mapRow(ResultSet rs, int i) throws SQLException {

                    return Optional.of(User.builder()
                            .id(rs.getLong("ID"))
                            .userName(rs.getString("USER_NAME"))
                            .email(rs.getString("EMAIL"))
                            .password(rs.getString("PASSWORD"))
                            .lockUser(rs.getString("LOCK_USER"))
                            .countInvalid(rs.getInt("COUNT_INVALID"))
                            .build());
                }
            });
        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();
        }
    }

    public int UpdateCountUser(Long id,int count){
        StringBuilder sql = new StringBuilder();

        sql.append("update USER  set count_invalid = :count \r\n");
        sql.append("where id = :id \r\n");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("count", count);
        parameterSource.addValue("id", id);

        return this.namedParameterJdbcTemplate.update(sql.toString(),parameterSource);


    }

    public int lockUser(Long id){
        StringBuilder sql = new StringBuilder();

        sql.append("update USER  set lock_user= :lockUser \r\n");
        sql.append("where id = :id \r\n");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("lockUser", ApplicationConstant.FLAG.YES);
        parameterSource.addValue("id", id);

        return this.namedParameterJdbcTemplate.update(sql.toString(),parameterSource);


    }
}
