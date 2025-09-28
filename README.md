# mock-services
Dummy app to mock services.
# User service
Given a user_id , returns 
{
    user_id: Long,
    username: String,
}
# Branch service
Given a branch_id , returns 
{
    branch_id: Long,
    branch_name: String,
}
# Attention service
Given an attention_id , returns 
{
    attention_id: Long,
    patient_id: Long,
    protocol: String,
    coverage_id: Long,
    practices: [
                {
                    practice_id: Long,
                    coverage_type: String,
                    practice_description: String,
                    quantity: Integer,
                    unit_price: Double,
                    coverage: Double 
                    coinsurance: Double
                },
                ]
}
# Patient service
Given a patient_id , returns 
{
    patient_id: Long,
    patient_name: String,
    dni: Integer,
}
# Coverage service
Given a coverage_id, List<practice_id>, returns 

{
    coverage_id: Long,
    coverage_name: String,
}

