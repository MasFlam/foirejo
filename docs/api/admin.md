# Administration Resource
- Path prefix: `/api/admin`
- Required roles: `admin`

### GET `/reviews`
Get the reported reviews.
- Request body: `Paginated`
- Response body: `Page(Review)`

### GET `/trade_messages`
Get the trade messages reported as inappropriate.
- Request body: `Paginated`
- Response body: `Page(TradeMessage)`

### GET `/disputes`
Get the trades for which there are disputes open.
- Request body: `Paginated`
- Response body: `Page(Trade)`

### GET `/offers`
Get the offers awaiting acceptation from the admins.
- Request body: `Paginated`
- Response body: `Page(Offer)`
