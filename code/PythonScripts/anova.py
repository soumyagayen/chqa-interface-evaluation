import pandas as pd
import statsmodels.api as sm
from statsmodels.formula.api import ols

# Example data for the 8x8 Latin Square Design
# Replace with your actual data
# Row, Column, Treatment, and Value should have 64 entries each to represent the 8x8 design
data = {
    'Row': [1, 1, 1, 1, 1, 1, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2,
            3, 3, 3, 3, 3, 3, 3, 3,
            4, 4, 4, 4, 4, 4, 4, 4,
            5, 5, 5, 5, 5, 5, 5, 5,
            6, 6, 6, 6, 6, 6, 6, 6,
            7, 7, 7, 7, 7, 7, 7, 7,
            8, 8, 8, 8, 8, 8, 8, 8],
    'Column': [1, 2, 3, 4, 5, 6, 7, 8,
               1, 2, 3, 4, 5, 6, 7, 8,
               1, 2, 3, 4, 5, 6, 7, 8,
               1, 2, 3, 4, 5, 6, 7, 8,
               1, 2, 3, 4, 5, 6, 7, 8,
               1, 2, 3, 4, 5, 6, 7, 8,
               1, 2, 3, 4, 5, 6, 7, 8,
               1, 2, 3, 4, 5, 6, 7, 8],
    'Treatment': ['A', 'B', 'H', 'C', 'G', 'D', 'F', 'E', 
                 'B', 'C', 'A', 'D', 'H', 'E', 'G', 'F', 
                 'C', 'D', 'B', 'E', 'A', 'F', 'H', 'G', 
                'D', 'E', 'C', 'F', 'B', 'G', 'A', 'H', 
                'E', 'F', 'D', 'G', 'C', 'H', 'B', 'A', 
                'F', 'G', 'E', 'H', 'D', 'A', 'C', 'B', 
                'G', 'H', 'F', 'A', 'E', 'B', 'D', 'C', 
                'H', 'A', 'G', 'B', 'F', 'C', 'E', 'D'],
    'Value': [2, 1, 2, 2, 1, 2, 1, 2,
             2, 2, 2, 2, 1, 1, 1, 2,
             2, 1, 0, 2, 1, 1, 1, 1,
             2, 1, 2, 2, 1, 1, 1, 1,
             2, 1, 2, 2, 1, 2, 1, 1,
             2, 2, 2, 0, 0, 2, 1, 1,
             0, 1, 2, 2, 0, 1, 1, 2,
             2, 1, 2, 2, 1, 2, 1, 2]
    }
        
        #Create DataFrame
df = pd.DataFrame(data)

# Fit the model
model = ols('Value ~ C(Row) + C(Column) + C(Treatment)', data=df).fit()

# Perform the ANOVA
anova_table = sm.stats.anova_lm(model, typ=2)
print(anova_table)
