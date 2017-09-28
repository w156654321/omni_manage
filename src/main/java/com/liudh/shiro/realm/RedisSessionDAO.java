/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.liudh.shiro.realm;

import com.dubbo.utils.SerializeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 名称：RedisSessionDAO
 * <p>
 * 创建者
 * 创建时间
 * 创建描述
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    private RedisManager redisManager;

    /**
     * shiro-redis的session对象前缀
     */
    private String keyPrefix = "shiro_redis_session:";

    /**
     * 构造函数
     * @param redisManager  redisManager
     */
    public RedisSessionDAO(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
    /**
     *
     * @param session       session
     * @throws UnknownSessionException  未知session错误
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     * @param session       session
     * @throws UnknownSessionException     未知session错误
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if(session == null || session.getId() == null){
            logger.error("保存或修改session时，session或session id为null");
            return;
        }
        byte[] key = getByteKey(session.getId());
        byte[] value = SerializeUtil.serialize(session);
        session.setTimeout(redisManager.getExpire() * 1000L);
        this.redisManager.set(key, value, redisManager.getExpire());
    }

    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            logger.error("删除session时，session或session id为null！");
            return;
        }
        redisManager.del(this.getByteKey(session.getId()));
    }

    /**
     * 手动删除session信息，达到控制一个账号同时只有一个人登录
     * @param sessionId            sessionId
     */
    public void manualDelSession(String sessionId){
        redisManager.del(this.getByteKey(sessionId));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        /*Set<byte[]> keys = redisManager.keys(this.keyPrefix+"*");
        if(!keys.isEmpty()){
            for(byte[] key:keys){
                Session s = (Session)SerializeUtil.deserialize(redisManager.get(key));
                sessions.add(s);
            }
        }*/
        return sessions;
    }

    /**这两个是AbstractSessionDAO内的*/
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("在doReadSession时，session id是null!");
            return null;
        }
        return (Session)SerializeUtil.deserialize(redisManager.get(this.getByteKey(sessionId)));
    }

    /**
     * 获得byte[]型的key
     * @param sessionId     sessionId
     * @return              二进制
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
}