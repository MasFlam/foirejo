# Trades Resource
Path prefix: `/api/trades`

### GET `/`
- Response body: `Trade[]`

### GET `/{tradeId}`
- Response body: `Trade`

### POST `/`
- Request body: `Trade`
- Response body: `Trade`

### PUT `/{tradeId}`
- Request body: `Trade`
- Response body: `Trade`

### DELETE `/{tradeId}`

### GET `/{tradeId}/messages`
- Response body: `TradeMessage[]`

### POST `/{tradeId}/message`
- Request body: `TradeMessage`
