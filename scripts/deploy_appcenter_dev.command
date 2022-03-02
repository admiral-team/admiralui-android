
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )" 

cd "$DIR"
cd ".."

read -p "Enter issue name(Press enter to skip this step): " ISSUE_NAME

read -p "Skip building and archiving app(y/n)? " SKIP_ARCHIVE_STRING
SKIP_ARCHIVE=false
if [ "$SKIP_ARCHIVE_STRING" == "y" ] || [ "$SKIP_ARCHIVE_STRING" == "yes" ]; then SKIP_ARCHIVE=true; fi

read -p "Skip Jira comment(y/n)? " SKIP_JIRA_COMMENT_STRING
SKIP_JIRA_COMMENT=false
if [ "$SKIP_JIRA_COMMENT_STRING" == "y" ] || [ "$SKIP_JIRA_COMMENT_STRING" == "yes" ]; then SKIP_JIRA_COMMENT=true; fi

bundle exec fastlane deploy_appcenter_dev issue_name:"$ISSUE_NAME" skip_archive:"$SKIP_ARCHIVE" skip_jira_comment:"$SKIP_JIRA_COMMENT"