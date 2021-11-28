# Reviews Resource
- Path prefix: `/api/reviews`

### GET `/{reviewId}`
- Response type: `Review`
- Not authenticated

### DELETE `/{reviewId}`
Delete the review.
- Required role: `admin`

### POST `/{reviewId}/report`
Report the review as inappropriate.

### POST `/{reviewId}/unreport`
Mark the review as appropriate.
- Required roles: `admin`
