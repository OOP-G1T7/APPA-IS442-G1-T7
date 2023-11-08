Database Setup for oop-g1t7-db

Run deploy.sql on a MySQL Server to create the oop-g1t7-db database and tables

===============================================================================
database name: oop-g1t7-db

tables to be created:
1. user
-stores the user information
columns: user_id (pk), email, password, and role

2. portfolio
- stores user portfolios information
columns: portfolio_id (pk), user_id (fk), portfolio name, description, capital

3. portfolio_stock
- stores stock within portfolio with its proportion
- proportion should total up to 100
columns: portfolio_id (pk, fk), ticker (pk), proportion

4. audit_log
- stores log of user activities
columns: log_id (pk), user_id (fk), timestamp, detail, outcome

5. password_reset_token
- stores password reset token information
columns: id (pk), user_id (fk), expiry_date, token

6. password_reset_token_seq
- manage the sequence of password reset tokens
columns: next_val (pk)
