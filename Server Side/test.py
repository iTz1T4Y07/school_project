import db_queries, datetime
from global_vars import ErrorCodes
"""
data = {"Error:": "0", "Error Description": "None"}
data["Args"] = [{"Beach ID": 1, "Beach Name": "Palmahim"}, {"Beach ID": 2, "Beach Name": "Bat-Yam"}]
print(data)
"""

"""result, conn = db_queries.execute_statement(db_queries.UPDATE_EVENT1,
                                            {
                                                "a": 2,
                                                "user_id": 1,
                                                "b": "Morning_Count",
                                                "event_id": 1
                                             }
                                            )
"""                                           """
statement = db_queries.REMOVE_USER_FROM_EVENT.format(day_part_count="Morning_Count",
                                                     day_part_users="Morning_Participants")
result, conn = db_queries.execute_statement(statement, {"user_id": 2, "event_id": 3})
"""

def test():
    return 1,"str"

a = test()
print(a)




