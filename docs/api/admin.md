# Administration Resource
- Path prefix: `/api/admin`
- Required roles: `admin`

### GET `/offer_reviews`
Get the unadressed reports of offer reviews.
- Response body: `OfferReviewReport[]`

### GET `/trade_messages`
Get the trade messages reported as inappropriate.
- Response body: `TradeMessage[]`

### GET `/disputes`
Get the trades for which there are disputes open.
- Response body: `Trade[]`

### GET `/offers`
Get the offers awaiting acceptation from the admins.
- Response body: `Offer[]`
