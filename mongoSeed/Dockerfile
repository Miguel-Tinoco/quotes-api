FROM mongo

# Copy necessary seeding script and data file into the container
COPY /50k_quotes_data.json /50k_quotes_data.json

# Define command to run during container startup
CMD mongoimport --host mongodb --db quoteGarden --collection Quotes --type json --file /50k_quotes_data.json --jsonArray
