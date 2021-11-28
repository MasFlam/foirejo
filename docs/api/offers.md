# Offers Resource
- Path prefix: `/api/offers`

### GET `/`
- Response body: `Offer[]`
- Not authenticated

### GET `/{offerId}`
- Response body: `Offer`
- Not authenticated

### GET `/{offerId}/long_desc`
- Response body: TBD
- Not authenticated

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
- Not authenticated

### POST `/{offerId}/review/{reviewId}/report`
Report the review as inappropriate.

### POST `/{offerId}/review/{reviewId}/unreport`
Mark the review as appropriate.
- Required roles: `admin`

### GET `/{offerId}/review/{reviewId}`
- Response type: `OfferReview`
- Not authenticated

### DELETE `/{offerId}/review/{reviewId}`
Delete the review.
- Required role: `admin`

### GET `/{offerId}/review`
- Response body: `OfferReview` or `null`

### POST `/{offerId}/review`
- Request body: `OfferReview`

## DELETE `/{offerId}/review`
