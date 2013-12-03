class Sponsor < ActiveRecord::Base
  has_many :clinical_studies
  has_many :sites
end
