package googy.googyspring.repository;


import googy.googyspring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class JdbcMemberRepository  implements MemberRepository {
    private DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO MEMBER(name) VALUES(?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection =  getConnection();

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, member.getName());
            preparedStatement.executeUpdate(); // 실제 쿼리가 수행됨
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                member.setId(resultSet.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            close(connection, preparedStatement, resultSet);
        }
        return member;
    }
    public void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        // close
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM MEMBER WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Member member = new Member();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                member.setId(resultSet.getLong("id"));
                member.setName(resultSet.getString("name"));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return Optional.of(member);
//        return Optional.empty();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM MEMBER";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Member> memberList = new ArrayList<>();
        try {
            connection =  getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Member member = new Member();
                member.setId(resultSet.getLong("id"));
                member.setName(resultSet.getString("name"));
                memberList.add(member);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return memberList;
    }
}
