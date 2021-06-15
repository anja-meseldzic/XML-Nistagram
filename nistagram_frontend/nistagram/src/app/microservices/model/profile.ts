export class Profile {
  constructor(
    public id: number,
    public verified: boolean,
    public active: boolean,
    public allowTagging: boolean,
    public regularUserUsername: string,
    public allowMessages: boolean,
    public privateProfile: boolean
  ) {
  }
}
