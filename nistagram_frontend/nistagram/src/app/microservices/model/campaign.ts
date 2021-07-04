import { CampaignDetails } from "./campaign-details";

export class Campaign {
    constructor(
        public id : number,
        public mediaId : number,
        public link : string,
        public start : Date,
        public targetedGenders : string[],
        public targetedAges : number[],
        public details : CampaignDetails
    ) {}
}
