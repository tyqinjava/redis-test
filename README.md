﻿## Redis学习**一、redis的五种数据类型*** 字符串（String）* 哈希（hash）* 字符串列表（list）* 字符串集合（set）* 有序字符串集合     **二、存储String（最长不能超过256m）**                .key不能过长（消耗的内存，影像查询效率）     .最好有统一的命名规范     .nil 表示没有value        1. 赋值 set key value           set name tom   2. 取值 get key                 get name   3. 取值并赋值 getset key         getset name jack    4. 删除 del key                 del name   5. 自增1 incr key               incr age    6. 自减1 decr key               decr age   7. incrby key number           incrby age 5     **三、 存储Hash（相当于字符串为key，字符串为value的hashmap容器）**  1. 赋值 hset key field vaule,...            hset hash_key username jack  2. 取值 hget key field                      hget hash_key username --> "jack"  3. 取值 hmget key field1,field2,..          hmget hash_key username age --> "jack" "10"  4. 取值 hgetall hash_key                    hgetall hash_key username "jack" age "10"  5. 删除 hdel hash_key field1,field2,..      hdel hash_key username age   6. 删除 del hash_key                        del hash_key 删除所有集合  7. 自增 hincrby hash_key field number       hincrby hash_key age 5  age字段加5            8. 是否存在 hexists hash_key field           hexists hash_key username 判断username字段是存在  9. 长度 hlen hash_key                       返回hash容器的属性个数  10. 字段 hkeys hash_key                      返回hash容器中所有field的名  11. 值 hvals hash_key                       返回hash容器所有的值 **四、存储List（相当于字符串链表）**  > 数组形式的list 插入，删除慢，取值快  > 双向链表形式的list 插入，删除快，取值慢  1. 左端添加 lpush mylist a b c 没有就创建，有则继续添加   2. 右段添加 rpush mylist a b c 没有就创建，有则继续添加   3. 查看列表 lrange mylist start end  查看list   4. lpop mylist 弹出最左边的元素  5. rpop mylist 弹出最右边的元素  6. llen mylist 返回list长度  7. lrem mylist count vaule 删除mylist中count个值；  8. lset   9. linsert mylist before|after pivot value 在pivot之前或者之后插入元素  10. rpoplpush list1 list2 将list1弹出压入list2中  11. ltrim key start end  去除start 到end个元素      **五、存储Set**  > 没有排序的字符串的集合  > 不允许有重复值  1. sadd myset a b c 向myset中添加元素  2. srem myset 1 2 删除myset中1，2元素  3. smembers myset 返回myset所有元素  4. sismember myset a 判断myset中是否存在a元素  5. sinter set1 set2 返回set1和set2中交集  6. sunion set1 set2 返回set1和set2中并集   7. sdiff set1 set2 返回set1中不同于set2中的元素  8. scard myset 返回set中的元素个数  9. srandmember myset 随机返回myset中的元素  10. sdiffstore set1 set2 set3 将set2中不存在于set3中的元素存入set1中  11. sinterstore set1 set2 set3 将set2和set3中的元素的交集存入set1中  12. sunionstore set1 set2 set3 将set2和set3中的元素并集存入set1中**六、排序Set（sorted-set）**  > sorted-sort 中存在分数的概念，根据分数来排名，分数是可以重复的   1. zadd mysort 70 zhangshang 80 lisi 添加：zhangshang分数为70，lisi分数为80，如果存在则覆盖 2. zscore mysort zhangshang 获取zhangshang的分数 3. zcard mysort 获取元素个数 4. zrem mysort zhangshang 删除zhangshang 5. zrange mysort 0 -1  获取0到-1位置的元素 排序由小到大  6. zrevrange mysort 0 -1 withscores 获取0到-1位置的元素 排序由大到小 7. zremrangebyrank mysort 0 4 删除0 4的元素 8. zremrangebyscore mysort 80 100  删除分数在80到100的元素 9. zrangebyscore mysort 0 100 withscores 获取分数在0到100的元素 10. zrangebyrscore mysort 0 100 withscore limit 0 2 获取分数在0到100的元素只获取两条 11. zcount mysort 80 90 获取mysort中分数为80到90中元素的个数     **七、Keys同用操作**1. keys * 获取redis所有的key（不建议再生产环境下使用，占用CPU）操作时间复杂度O(n)2. keys my? 获取以my开头的key O(n)3. del my1 删除key为my1的数据 O(1)4. exists my1 返回0和1 1表示存在，0表示不存在 O(1)5. rename oldkey newkey 重命名key O(1)6. expire key 1000 设置可以的有效时间单位秒 O(1)7. ttl key 获取key剩余有效时间 O(1)8. type key 获取key的数据结构类型 O(1)9. flushall 清空数据库 O(1)10. dbsize 计算数据库大小 key数量 O(1)11. persist key 去掉过期时间 O(1)**八、Redis特性** - Redis是多数据库的结构    - 一个redis实例可以包涵多个数据库    - 客户端可以指定连接那个数据库    - 一个redis实例可以提供16个数据    - 默认连接0号数据库    - 使用select选择数据库 select 0 选择0号数据库。    - move myset 1 将本数据库中的myset移动到1号数据库 - Redis事务  multi,exec,discard        ````      - multi 开启事务，之后的所有命令都会存入队列中      - 使用exec命令后执行（相当于commit）。     - discard（相当于回滚）    ````**九、Redis的持久化**      1. RDB方式（指定时间将内存中快照的数据保存在文件中      - RDB如果出现服务器宕机，数据不能完证保存      - RDB配置（默认配置）           - save 900 1 每900秒当有一个key发生变化就持久化一次           - save 300 10 每300秒至少有10个key发生变化就持久化一次           - save 60 10000 每60秒至少有10000个key发生变化就持久化一次           - dbfilename dump.rdb 数据库文件           - ./ 当前路径生成保存   2. AOF方法（使用日志的方法记录每个一个操作）      - AOF方式 数据安全性      - AOF配置         - appendonly no 修改为 yes         - 默认生成appendonly.aof 文件         - 同步策略             - appendfsync always 每修改一次同步到磁盘中            - appendfsync everysec 每秒同步一次到磁盘中            - appendfsync no   不同步    3. 无持久化（不持久化 相当于缓存） ## Redis精通    *redis是单线程应用**一、 Redis 配置**  1. daemonize 是否守护线程启动  2. port  端口  3. logfile 日志文件目录  4. dir 指定redis的工作目录    *使用redis客户端命令 config get * 可以查看所有的配置信息  二、数据结构的内部编码   1. string -> raw,int,embstr   2. hash   -> hashtable,ziplist   3. list   -> linkedlist,ziplist   4. set    -> hashtable,intset   5. zset   -> skiplist,ziplist   >   redis 为了减少内存的占用，会对数据进行压缩      redis 源码中redisObject结构体       struct redisObject {         类型 （对外的数据类型）string,hash,list,set,zset      编码 (内部编码类型) hashtable，ziplist ...      数据指针      虚拟内存      其他信息      .      .      .      }    redis 单线程理解   1. redis每次只能执行一条命令，不能执行命令只能等待   2. 也就是说如果有一条命令发生阻塞，那整个redis都会阻塞   3. redis使用的是IO多路复用功能，NIO   三、redis 数据类型（高级）> **key的value值不宜设置过大，会导致巨大网络开销，对redis造成阻塞，建议再100k以内** **1）string类型**        1. string  类型使用场景       . 缓存       . 计数器       . 分布式锁            get set del O(1) 级别操作     incr +1 decr -1,incrby +n,decrby -n                reids业务场景     //缓存     public VideoInfo get(Long id) {                String redisKey  = redisPrefix +id;        VideoInfo videoInfo = redis.get(redisKey);        if(videoInfo == null) {            videoInfo= mysql.get(id);            if(videoInfo != null) {                redis.set(redisKey,serialize(videoInfo));            }        }             return videoInfo;     }     //分布式ID生成器     根据reids原子单线程特性，可以使用incr进行ID生成      2. set setnx setxx   3. set key value  不管key是否存在都设置   4. setnx key value key不存在才设置   5. set key value xx  key存在设置   6. setex 设置key value同时设置过期时间      7. mget，mset (批量操作,原子操作)  O(n)   8. mget key1 key2 key3       **n次get = n次网络时间 + n次命令时间**      9. getset key value 设置新值返回旧值   10. append key value 追加新值   11. strlen key value 返回字符长度 O(1)      12. incrbyfloat getrange setrange 不常用       **2） hash类型**      1. 命令操作参考基础篇   2. 和string类型对比            string+json 和 hash对比   效率    很高           高   容量    低             低   灵活性  差             高   序列化  简单           复杂   **当需要频繁更改对象中的属性值时候可以使用hash，   不需要的时候就可以使用string + json保存**      3.实现缓存的三种种方式   . 使用string类型： key：user:1 value使用json,xml,..保存      优点：编程简单，可以节约内存，缺点：设置属性要操作整个属性      . 使用string类型   key：user:1:name value "tom"                     key: user:1:age  value 40       优点：直观，可以部分更新 ，缺点，内存占用比较大，key分散                                      . 使用hash类型     key: user:1 hash              优点：直观，可以部分更新 ，缺点，编程稍微复杂，ttl不好控制                                                 **3）列表list**                        blpop brpop 阻塞式弹出      **4) set和zset 参考基础篇**         