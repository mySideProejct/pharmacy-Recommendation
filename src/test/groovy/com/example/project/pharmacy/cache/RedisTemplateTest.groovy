package com.example.project.pharmacy.cache

import com.example.project.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SetOperations
import org.springframework.data.redis.core.ValueOperations


// 레디스를 사용해야 하므로, 우리는 컨테이너를 띄워야 한다.
class RedisTemplateTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    private RedisTemplate redisTemplate

    def "RedisTemplate String operations"() {
        /*
        stringKey : hello
        * */
        given:
        def valueOperations = redisTemplate.opsForValue()
        def key = "stringKey"
        def value = "hello"

        when:
        valueOperations.set(key, value)

        then:
        def result = valueOperations.get(key)
        result == value
    }

    def "RedisTemplate set operations"() {
        /*
        key
            "h", "e", "l", "o" ...
        * */
        given:
        def setOperations = redisTemplate.opsForSet()
        def key = "setKey"

        when:
        setOperations.add(key, "h", "e", "l", "l", "o")

        then:
        setOperations.size(key) == 4

    }

    def "RedisTemplate has operations"() {
        /*
        key
            subkey : value1
            subkey2 : value2
            ...
        * */

        given:
        def hashOperations = redisTemplate.opsForHash()
        def key = "hashKey"

        when:
        hashOperations.put(key, "subkey", "value")
        hashOperations.put(key, "subkey2", "value2")

        then:
        hashOperations.get(key, "subkey") == "value"
        hashOperations.get(key, "subkey2") == "value2"


        def entries = hashOperations.entries(key)
        entries.keySet().contains("subkey")
        entries.values().contains("value2")

        hashOperations.size(key) == 2

    }
}
