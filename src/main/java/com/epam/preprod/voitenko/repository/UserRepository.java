package com.epam.preprod.voitenko.repository;

import com.epam.preprod.voitenko.entity.Role;
import com.epam.preprod.voitenko.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.DEFAULT_AVATAR;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_CREATE_USER;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_DELETE_USER;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_ALL_USERS;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_USER_BY_EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_GET_USER_BY_ID;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.CANNOT_UPDATE_USER;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ADDRESS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.AVATAR;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.BAN_EXPIRATION_DATE;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.FULL_NAME;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ID;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.PASSWORD;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.PHONE_NUMBER;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ROLE;
import static com.epam.preprod.voitenko.constant.Constatns.PATH_TO_AVATARS;
import static com.epam.preprod.voitenko.util.ServiceUtil.getHashPassword;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserRepository implements GeneralRepository<UserEntity, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(UserRepository.class);

    private static final String SQL_SELECT_ALL = "SELECT * FROM users;";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id=?;";
    private static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?;";
    private static final String SQL_UPDATE_USER = "UPDATE users SET email=?, password=?, fullName=?, phoneNumber=?, address=?, banExpirationDate=? WHERE id=?;";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id=?;";
    private static final String SQL_INSERT_USER = "INSERT INTO users (email, password, fullName, phoneNumber, address, avatar) VALUES (?, ?, ?, ?, ?, ?);";

    @Override
    public List<UserEntity> getAll(Connection connection) {
        List<UserEntity> users = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(extractUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_ALL_USERS, e);
        } finally {
            close(resultSet, statement);
        }
        return users;
    }

    @Override
    public UserEntity getEntityById(Connection connection, Integer id) {
        UserEntity userEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_GET_USER_BY_ID);
            statement.setInt(++index, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userEntity = extractUser(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_USER_BY_ID, e);
        } finally {
            close(resultSet, statement);
        }
        return userEntity;
    }


    @Override
    public UserEntity update(Connection connection, UserEntity entity) {
        checkObjectIsNull(entity);
        UserEntity oldValue = getEntityById(connection, entity.getId());
        if (oldValue == null) {
            return null;
        }
        String hashPassword = entity.getPassword();
        if (!oldValue.getPassword().equals(entity.getPassword())) {
            hashPassword = getHashPassword(hashPassword);
        }
        PreparedStatement statement = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(++index, entity.getEmail());
            statement.setString(++index, hashPassword);
            statement.setString(++index, entity.getFullName());
            statement.setString(++index, entity.getPhoneNumber());
            statement.setString(++index, entity.getAddress());
            statement.setTimestamp(++index, entity.getBanExpirationDate());
            statement.setInt(++index, oldValue.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_USER, e);
        } finally {
            close(statement);
        }
        return oldValue;
    }

    @Override
    public boolean delete(Connection connection, Integer id) {
        PreparedStatement statement = null;
        int rowsAffected = -1;
        try {
            statement = connection.prepareStatement(SQL_DELETE_USER);
            int index = 0;
            statement.setInt(++index, id);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_USER, e);
            return false;
        } finally {
            close(statement);
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean create(Connection connection, UserEntity entity) {
        checkObjectIsNull(entity);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int index = 0;
            statement = connection.prepareStatement(SQL_INSERT_USER, RETURN_GENERATED_KEYS);
            statement.setString(++index, entity.getEmail());
            statement.setString(++index, getHashPassword(entity.getPassword()));
            statement.setString(++index, entity.getFullName());
            statement.setString(++index, entity.getPhoneNumber());
            statement.setString(++index, entity.getAddress());
            Object avatar = null;
            if (!DEFAULT_AVATAR.equals(entity.getAvatar())) {
                avatar = entity.getAvatar();
            }
            statement.setObject(++index, avatar);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                int id = (int) resultSet.getLong(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_CREATE_USER, e);
            return false;
        } finally {
            close(resultSet, statement);
        }
        return true;
    }

    public UserEntity getUserByEmail(Connection connection, String email) {
        checkObjectIsNull(email);
        UserEntity userEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_GET_USER_BY_EMAIL);
            int index = 0;
            statement.setString(++index, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userEntity = extractUser(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_GET_USER_BY_EMAIL, e);
        } finally {
            close(resultSet, statement);
        }
        return userEntity;
    }

    private UserEntity extractUser(ResultSet resultSet) throws SQLException {
        checkObjectIsNull(resultSet);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getInt(ID));
        userEntity.setEmail(resultSet.getString(EMAIL));
        userEntity.setPassword(resultSet.getString(PASSWORD));
        userEntity.setFullName(resultSet.getString(FULL_NAME));
        userEntity.setPhoneNumber(resultSet.getString(PHONE_NUMBER));
        userEntity.setAddress(resultSet.getString(ADDRESS));
        userEntity.setBanExpirationDate(resultSet.getTimestamp(BAN_EXPIRATION_DATE));
        userEntity.setRole(Role.valueOf(resultSet.getString(ROLE)));
        Object avatar = resultSet.getObject(AVATAR);
        if (avatar != null) {
            String fullPath = System.getProperty("user.dir") + PATH_TO_AVATARS + avatar.toString();
            if (Paths.get(fullPath).toFile().exists()) {
                userEntity.setAvatar(avatar.toString());
            }
        }
        return userEntity;
    }

    private void checkObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}