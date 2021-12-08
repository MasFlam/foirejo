# Reviews Resource
- Path prefix: `/api/reviews`

### GET `/{reviewId}`
- Response body: `Review`
- Not authenticated

### DELETE `/{reviewId}`
Delete the review.
- Required roles: `admin`

### GET `/{reviewId}/reporters`
Get all users who reported the review as inappropriate.
- Request body: `Paginated`
- Response body: `Page(User)`
- Required roles: `admin`

### POST `/{reviewId}/report`
Report the review as inappropriate.

### POST `/{reviewId}/unreport`
Mark the review as appropriate.
- Required roles: `admin`
