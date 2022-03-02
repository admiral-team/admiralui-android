DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd "$DIR"
cd ".."

bundle exec fastlane start_next_release_version