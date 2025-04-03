local key = KEYS[1]       -- 商品唯一标识
local buyer = ARGV[1]     -- 当前抢购用户 ID

if redis.call("EXISTS", key) == 1 then
    return 0    -- 商品已被抢购，返回失败
else
    redis.call("SET", key, buyer)  -- 记录抢购成功的用户
    return 1    -- 抢购成功
end
