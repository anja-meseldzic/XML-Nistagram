import { CampaignDetails } from "./campaign-details";
import { Report } from "./report";

export class Campaign {
    constructor(
        public id : number,
        public mediaId : number,
        public link : string,
        public start : Date,
        public targetedGenders : string[],
        public targetedAges : number[],
        public details : CampaignDetails,
        public report : Report
    ) {}
}
