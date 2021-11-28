# Administration Resource
- Path prefix: `/api/admin`
- Required roles: `admin`

### GET `/offer_reviews`
Get the unadressed reports of offer reviews.
- Response body: `OfferReviewReport[]`

### GET `/disputes`
Get the trades for which there are disputes open.
- Response body: `Trade[]`

### GET `/offers`
Get the offers awaiting acceptation from the admins.
- Response body: `Offer[]`
