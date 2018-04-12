package com.tangj.springcloud.dao.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tangj.springcloud.common.dao.jpa.BaseRepository;
import com.tangj.springcloud.entity.UserDTO;
import com.tangj.springcloud.model.UserModel;

public interface UserRepository extends BaseRepository<UserDTO, Long> {

	UserDTO findByUserName(String userName);

	UserDTO findByUserNameLike(String userName);

	UserDTO findByUserNameOrEmail(String userName, String email);

	Page<UserDTO> findByUserName(String userName, Pageable pageable);

	List<UserDTO> findByUserName(String userName, Sort sort);

	/**
	 * 1.自定义的SQL,在SQL的查询方法上面使用@Query注解 2.如涉及到删除和修改在需要加上@Modifying 3.timeout 查询超时
	 * 
	 * 此处的表名需和实体类的名称一致
	 */
	@Transactional(timeout = 10)
	@Modifying
	@Query("delete from UserDTO where id = ?1")
	void deleteByUserId(Long id);

	@Query("select u.id as id, u.userName as userName, u.passWord as passWord, u.email as email, u.nickName as nickName, u.regTime as regTime from UserDTO u")
	List<Map<String, Object>> testGetMapList();

	@Query("select new map(u.id as id, u.userName as userName, u.passWord as passWord, u.email as email, u.nickName as nickName, u.regTime as regTime) from UserDTO u")
	List<Map<String, Object>> testGetMapList2();

	@Query("select u.id as id1, u.userName as userName1, u.passWord as passWord1, u.email as email1, u.nickName as nickName1, u.regTime as regTime1 from UserDTO u")
	List<Map<String, Object>> testGetMapList3();

	@Query("select new com.tangj.springcloud.model.UserModel(u.id as id, u.userName as userName, u.passWord as passWord) from UserDTO u")
	List<UserModel> testGetModelList();

	@Query("select u from UserDTO u")
	List<UserDTO> testGetAllField();

	@Query("select u.id,u.userName from UserDTO u")
	List<UserDTO> testGetSomeField();

	/**
	 * 使用原生sql
	 * 
	 * 这种只能查询全部的字段
	 */
	@Query(value = "select u.* from user u", nativeQuery = true)
	List<UserDTO> testNativeQuery();

	/** 使用原生sql */
	@Query(value = "select u.id, u.user_name, u.pass_word, u.email, u.nick_name, u.reg_time from user u", nativeQuery = true)
	List<UserDTO> testNativeQuery2();

	@Query(value = "select u.id,u.user_name,u.pass_word,u.email,u.nick_name from user u", nativeQuery = true)
	List<Object> testNativeQuery3();

	// @Query(value = "select u.id,u.user_name,u.pass_word,u.email,u.nick_name from user
		// u", nativeQuery = true)
	@Query(value = "select u.user_name as userName,u.pass_word as passWord,u.email as email,u.nick_name as nickName from user u", nativeQuery = true)//错误
	List<UserModel> testNativeQuery4();

}