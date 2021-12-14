# Search Resource
- Path prefix: `/api/search`

### GET `/`
Search for different entities. This endpoint provides keyword and exact search, sorting,
and filtering.
- Request body: `Paginated(SearchRequest)`
- Response body: `Page(according type)`
- Not authenticated
