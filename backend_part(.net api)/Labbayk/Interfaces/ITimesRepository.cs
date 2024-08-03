using Labbayk.Models;

namespace Labbayk.Interfaces
{
    public interface ITimesRepository
    {
        ICollection<Time> GetTimes();
    }
}
