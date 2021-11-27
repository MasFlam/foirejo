# Offers Resource
Path prefix: `/api/offers`

### GET `/`
- Response body: `Offer[]`

### GET `/{offerId}`
- Response body: `Offer`

### GET `/{offerId}/long_desc`
- Response body: TBD

### POST `/`
- Request body: `Offer`
- Response body: `Long`

### PUT `/{offerId}`
- Request body: `Offer`
- Response body: `Long`

### PUT `/{offerId}/long_desc`
- Request body: TBD

### DELETE `/{offerId}`

### GET `/{offerId}/reviews`
- Response body: `OfferReview[]`

### POST `/{offerId}/review/{reviewId}/report`
Report the review as inappropriate.

### GET `/{offerId}/review`
- Response body: `OfferReview` or `null`

### POST `/{offerId}/review`
- Request body: `OfferReview`

## DELETE `/{offerId}/review`
