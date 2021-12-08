# Trades Resource
- Path prefix: `/api/trades`

### GET `/{tradeId}`
- Response body: `Trade`

### POST `/new/{offerId}`
- Response body: `Trade`

### GET `/{tradeId}/messages`
- Request body: `Paginated`
- Response body: `Page(TradeMessage)`

### POST `/{tradeId}/messages`
- Request body: `TradeMessage`

### GET `/messages/{messageId}`
- Response body: `TradeMessage`

### POST `/messages/{messageId}/report`
Report the message as inappropriate.

### POST `/messages/{messageId}/unreport`
Mark the message as appropriate.
- Required roles: `admin`

### DELETE `/messages/{messageId}`
Delete a message.
- Required roles: `admin`

### POST `/{tradeId}/close`
Close the trade. Used before the buyer paid to cancel the trade, and to confirm
completion of the trade after the seller marks it as completed.

### GET `/{tradeId}/completed`
Get the completion proof the seller submitted.
- Response body: multipart of images

### POST `/{tradeId}/completed`
Mark the trade as completed (if it isn't yet) and submit proof of completion.
- Request body: multipart of images

### POST `/{tradeId}/dispute`
Open a dispute on the trade (only after the seller marks it as completed).
Can only be requested by the buyer.

### POST `/{tradeId}/dispute/cancel`
Cancel the dispute. Can only be requested by the buyer.

### POST `/{tradeId}/dispute/resolve_ok`
Resolve the dispute as the seller completing the trade.
- Required roles: `admin`

### POST `/{tradeId}/dispute/resolve_refund`
Resolve the dispute as the seller not actually completing the trade, refunding the buyer.
- Required roles: `admin`
