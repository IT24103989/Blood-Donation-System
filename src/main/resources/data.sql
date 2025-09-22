-- Create tables if they don't exist
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'donors')
BEGIN
CREATE TABLE donors (
                        id BIGINT IDENTITY(1,1) PRIMARY KEY,
                        first_name NVARCHAR(50) NOT NULL,
                        last_name NVARCHAR(50) NOT NULL,
                        nic_number NVARCHAR(20) NOT NULL UNIQUE,
                        blood_group NVARCHAR(5) NOT NULL,
                        contact_number NVARCHAR(15) NOT NULL,
                        email NVARCHAR(100),
                        address NVARCHAR(255),
                        is_active BIT DEFAULT 1
);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'donation_history')
BEGIN
CREATE TABLE donation_history (
                                  id BIGINT IDENTITY(1,1) PRIMARY KEY,
                                  donor_id BIGINT FOREIGN KEY REFERENCES donors(id),
                                  donation_date DATE NOT NULL,
                                  donation_location NVARCHAR(100) NOT NULL,
                                  hemoglobin_level DECIMAL(5,2),
                                  medical_officer NVARCHAR(100),
                                  notes NVARCHAR(MAX)
);
END