require './build_info.rb'

def replace(file:, regex:, with:)
  content = File.read(file)
  new_content = content.gsub(regex, with)
  File.open(file, "w") {|file| file.puts new_content }
end

def jira_get_done_issues(fix_version:)
  issues = jira_get_issues(
    project: ENV['JIRA_PROJECT_NAME'],
    labels: ['Android'],
    fix_version: fix_version,
    status: "Выполнено"
  )
end

def jira_issue_link(key:)
  File.join(ENV['JIRA_SITE'], 'browse', key)
end

def send_message(build_info:, chat_ids:)
  info = formatted_build_info_telegram(build_info: build_info)
  send_telegram_message(text: info["text"], chat_ids: chat_ids, url_buttons: info["url_buttons"])
end

def send_telegram_message(text:, chat_ids:, url_buttons:)
  telegram_send_message(
    api_token: ENV['TELEGRAM_API_TOKEN'],
    chat_ids: chat_ids,
    text: text,
    parse_mode: "HTML",
    url_buttons: url_buttons
  )
end