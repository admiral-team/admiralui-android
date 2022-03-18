# frozen_string_literal: true

source "https://rubygems.org"

git_source(:github) {|repo_name| "https://github.com/#{repo_name}" }

gem 'jira-ruby'
gem "liquid", '~> 5.0'
gem "rqrcode", '~> 2.0'
gem "fastlane", '2.198.0'
gem "telegram-bot-ruby", '~> 0.15.0'

plugins_path = File.join(File.dirname(__FILE__), 'fastlane', 'Pluginfile')
eval_gemfile(plugins_path) if File.exist?(plugins_path)





