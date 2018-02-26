/**
 * halburt
 */
package com.houdask.site.auth.shiro.session;

import com.houdask.site.common.utils.IdGen;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Repository("sessionIdGenerator")
public class MyIdGenerator implements  SessionIdGenerator {

	@Override
	public Serializable generateId(Session session) {
		return IdGen.uuid();
	}


}
