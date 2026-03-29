import json
import boto3
from botocore.exceptions import ClientError

def lambda_handler(event, context):
    ses_client = boto3.client('ses')

    for record in event['Records']:
        try:
            # Parse SQS message body
            message_body = json.loads(record['body'])

            # Extract email and content from the note
            recipient_email = message_body['creatorEmail']
            note_content = message_body['content']
            creator_name = message_body['creatorName']

            # Send email via SES
            response = ses_client.send_email(
                Source='noreply@mekh.click',
                Destination={
                    'ToAddresses': [recipient_email]
                },
                Message={
                    'Subject': {
                        'Data': 'Your Note',
                        'Charset': 'UTF-8'
                    },
                    'Body': {
                        'Text': {
                            'Data': f"Hello {creator_name},\n\nYour note:\n{note_content}",
                            'Charset': 'UTF-8'
                        }
                    }
                }
            )

            print(f"Email sent successfully to {recipient_email}")

        except ClientError as e:
            print(f"SES error: {e.response['Error']['Message']}")
        except KeyError as e:
            print(f"Missing required field: {e}")
        except json.JSONDecodeError:
            print("Invalid JSON in SQS message")
        except Exception as e:
            print(f"Unexpected error: {str(e)}")

    return {
        'statusCode': 200,
        'body': json.dumps('Processing complete')
    }
