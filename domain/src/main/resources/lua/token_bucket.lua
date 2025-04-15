-- lua脚本实现令牌桶限流购买
local key = KEYS[1]
local now = tonumber(ARGV[1])
local bucket_capacity = tonumber(ARGV[2])
local refill_rate = tonumber(ARGV[3])
local last_refill_time = tonumber(redis.call('hget', key, 'last_refill_time') or '0')
local tokens = tonumber(redis.call('hget', key, 'tokens') or '0')
local time_passed = now - last_refill_time
local new_tokens = math.floor(time_passed * refill_rate)
tokens = math.min(bucket_capacity, tokens + new_tokens)
if tokens >= 1 then
    tokens = tokens - 1
    redis.call('hset', key, 'last_refill_time', now)
    redis.call('hset', key, 'tokens', tokens)
    return 1
else
    return 0
end