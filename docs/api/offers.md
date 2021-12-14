# Offers Resource
- Path prefix: `/api/offers`

### GET `/{offerId}`
- Response body: `Offer`
- Not authenticated

### GET `/{offerId}/long_desc`
- Response body: TBD
- Not authenticated

### POST `/`
- Request body: `Offer/1`
- Response body: `Long`

### PUT `/{offerId}`
- Request body: `Offer/1`

### PUT `/{offerId}/long_desc`
- Request body: TBD

### DELETE `/{offerId}`

### GET `/{offerId}/reviews`
- Request body: `Paginated`
- Response body: `Page(Review)`
- Not authenticated

### GET `/{offerId}/review`
- Response body: `Review` or `null`

### POST `/{offerId}/review`
- Request body: `Review/1`

### DELETE `/{offerId}/review`
